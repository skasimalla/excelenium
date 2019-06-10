package com.as.selenium.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import com.as.selenium.api.NotificationService;

/**
 * Created by Tejas
 */
public class Reporting {

	static String ApplicationId;

	private static final ArrayList<Report> reports = new ArrayList<Report>();
	private static final ArrayList<String> respTime = new ArrayList<String>();

	public static void addInfo(String info) throws IOException {
		Report r = new Report("Info: " + info);
		reports.add(r);
	}

	public static void addRespInfo(String info) throws IOException {
		String r = new String(info);
		respTime.add(r);
	}

	public static void testStepFailed(String info) {
		Report r = new Report("Test failed: " + info);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		NotificationService.sendEmail(dtf.format(now), info);
		reports.add(r);
	}

	public static void reportSystemError(String error) {
		Report r = new Report("Error: " + error);
		reports.add(r);
	}

	public static void reportTestSuccess(String name) throws Exception {
		Report r = new Report("Test success: " + name);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		NotificationService.sendEmail(dtf.format(now), "Successful with no errors");

		reports.add(r);
	}

	public static String printReport() {
		StringBuilder sb = new StringBuilder();

		for (Report r : reports) {
			sb.append(r.getDescription());

			if (r.getDescription().startsWith("Test failed") || r.getDescription().startsWith("Error")) {
				StringBuilder failedTestReport = new StringBuilder();
				failedTestReport.append(r.getDescription());
				failedTestReport.append("\n");
				failedTestReport.append(sb);
				return failedTestReport.toString();
			}

			sb.append("\n");
		}

		return sb.toString();
	}

	public static String printResponseReport() {
		StringBuilder sb = new StringBuilder();

		for (String line : respTime) {
			sb.append(line);
			sb.append("\n");
		}

		return sb.toString();
	}

	private static class Report {
		private final String description;

		Report(final String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

	}

	public static void printArrayListToFile(ArrayList<String> arrayList, String filename) throws IOException {
		PrintWriter writer = new PrintWriter(filename);
		for (String line : arrayList) {
			writer.println(line);
		}
		writer.close();
	}

}
