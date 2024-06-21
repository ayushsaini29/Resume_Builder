package com.dashboard.config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;

@Configuration
public class GoogleCalendarConfig {

	private static final Logger logger = LoggerFactory.getLogger(GoogleCalendarConfig.class);

	private static final String APPLICATION_NAME = "Resume Builder";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);

	@Bean
	public Calendar googleCalendar() {
		try {
			final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			Credential credential = getCredentials(httpTransport);
			return new Calendar.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME)
					.build();
		} catch (GeneralSecurityException | IOException e) {
			logger.error("Failed to create Google Calendar client: ", e);
			throw new RuntimeException("Failed to create Google Calendar client", e);
		}
	}

	private Credential getCredentials(final NetHttpTransport httpTransport) throws IOException {
		// Load client secrets.
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
				new InputStreamReader(GoogleCalendarConfig.class.getResourceAsStream("/credentials.json")));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY,
				clientSecrets, SCOPES)
				.setDataStoreFactory(
						new com.google.api.client.util.store.FileDataStoreFactory(new java.io.File("tokens")))
				.setAccessType("offline").build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}
}
