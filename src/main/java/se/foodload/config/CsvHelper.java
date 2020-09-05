package se.foodload.config;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import se.foodload.domain.Item;
import se.foodload.repository.ItemRepository;

@Service
public class CsvHelper {
	@Autowired
	ItemRepository itemRepo;
	@Value("${item.csv.path}")
	private String csvPath;

	public List<Item> csvToItem() {
		System.out.println("hej");
		InputStream is = null;
		BufferedReader fileReader = null;
		CSVParser csvParser = null;
		Iterable<CSVRecord> csvRecords = null;

		try {
			is = new FileInputStream(csvPath);
		} catch (FileNotFoundException e) {
			System.out.println("1");
			e.printStackTrace();
		}
		try {
			fileReader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("2");
			e.printStackTrace();
		}

		try {
			csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withDelimiter(';'));
		} catch (IOException e) {
			System.out.println("3");

			e.printStackTrace();
		}
		try {
			csvRecords = csvParser.getRecords();
		} catch (IOException e) {
			System.out.println("4");

			e.printStackTrace();
		}
		int i = 0;
		for (CSVRecord csvRecord : csvRecords) {
			System.out.println(csvRecord.get(2));
			if (i == 7000) {
				break;
			}
			Item item = new Item(csvRecord.get(2), csvRecord.get(1), csvRecord.get(3));
			itemRepo.save(item);
			System.out.println(item);
			i++;

		}

		return null;

	}
}
