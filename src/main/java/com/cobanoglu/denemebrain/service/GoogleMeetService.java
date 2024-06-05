package com.cobanoglu.denemebrain.service;

import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.apps.meet.v2.CreateSpaceRequest;
import com.google.apps.meet.v2.Space;
import com.google.apps.meet.v2.SpacesServiceClient;
import com.google.apps.meet.v2.SpacesServiceSettings;
import com.google.auth.Credentials;
import com.google.auth.oauth2.ClientId;
import com.google.auth.oauth2.DefaultPKCEProvider;
import com.google.auth.oauth2.TokenStore;
import com.google.auth.oauth2.UserAuthorizer;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleMeetService {

    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/meetings.space.created");
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final String USER = "default";

    private static final TokenStore TOKEN_STORE = new TokenStore() {
        private Path pathFor(String id) {
            return Paths.get(".", TOKENS_DIRECTORY_PATH, id + ".json");
        }

        @Override
        public String load(String id) throws IOException {
            if (!Files.exists(pathFor(id))) {
                return null;
            }
            return Files.readString(pathFor(id));
        }

        @Override
        public void store(String id, String token) throws IOException {
            Files.createDirectories(Paths.get(".", TOKENS_DIRECTORY_PATH));
            Files.writeString(pathFor(id), token);
        }

        @Override
        public void delete(String id) throws IOException {
            if (!Files.exists(pathFor(id))) {
                return;
            }
            Files.delete(pathFor(id));
        }
    };

    private UserAuthorizer getAuthorizer(URI callbackUri) throws IOException {
        try (InputStream in = getClass().getResourceAsStream(CREDENTIALS_FILE_PATH)) {
            if (in == null) {
                throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
            }

            ClientId clientId = ClientId.fromStream(in);

            return UserAuthorizer.newBuilder()
                    .setClientId(clientId)
                    .setCallbackUri(callbackUri)
                    .setScopes(SCOPES)
                    .setPKCEProvider(new DefaultPKCEProvider() {
                        @Override
                        public String getCodeChallenge() {
                            return super.getCodeChallenge().split("=")[0];
                        }
                    })
                    .setTokenStore(TOKEN_STORE).build();
        }
    }

    private Credentials getCredentials() throws Exception {
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

        try {
            URI callbackUri = URI.create(receiver.getRedirectUri());
            UserAuthorizer authorizer = getAuthorizer(callbackUri);

            Credentials credentials = authorizer.getCredentials(USER);
            if (credentials != null) {
                return credentials;
            }

            URL authorizationUrl = authorizer.getAuthorizationUrl(USER, "", null);
            System.out.printf("Open the following URL to authorize access: %s\n", authorizationUrl.toExternalForm());

            // Manually open this URL in your browser and authorize the application
            String code = receiver.waitForCode();
            credentials = authorizer.getAndStoreCredentialsFromCode(USER, code, callbackUri);
            return credentials;
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Re-throw exception to be caught in the calling method
        } finally {
            receiver.stop();
        }
    }



    public String createGoogleMeetLink() throws Exception {
        try {
            Credentials credentials = getCredentials();
            if (credentials == null) {
                System.err.println("Failed to obtain credentials.");
                return "Error creating meeting: Failed to obtain credentials.";
            }

            SpacesServiceSettings settings = SpacesServiceSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build();

            try (SpacesServiceClient spacesServiceClient = SpacesServiceClient.create(settings)) {
                CreateSpaceRequest request = CreateSpaceRequest.newBuilder()
                        .setSpace(Space.newBuilder().build())
                        .build();
                Space response = spacesServiceClient.createSpace(request);
                return response.getMeetingUri();
            } catch (IOException e) {
                e.printStackTrace();
                return "Error creating meeting: " + e.getMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating meeting: " + e.getMessage();
        }
    }


}