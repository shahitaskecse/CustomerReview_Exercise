package de.hybris.platform.customerreview.newcode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import de.hybris.platform.customerreview.dao.CustomerReviewDao;

//to read the list of curse words from a config file that is comma separated

public class ReadFromConfig {
	private String filePath;

	@Required
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	protected String getFilePath() {
		return this.filePath;
	}

	public Set<String> getFromConfig() {
		Properties properties = new Properties();
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(filePath);
		try {
			properties.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException exp) {
				System.out.println(exp);
			}
		}
		String[] cursedWords = (String[]) ((String) properties.get("cursedWords")).split(",");
		Set<String> cursedWordsSet = new HashSet<String>(Arrays.asList(cursedWords));
		return cursedWordsSet;

	}
}
