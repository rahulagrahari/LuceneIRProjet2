/*
 * 
 * @author: rahul
 * 
 * created-date: 19/03/2018

*/ 



package com.groupIR.IR2.indexer;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.groupIR.IR2.parser.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class IndexFile {
	
	private IndexFile() {
	}

	/** Index all text files under a directory. 
	 * @throws ParserConfigurationException 
	 * @throws SAXException */
	public static void callIndex(String docsPath, String indexPath) throws SAXException, ParserConfigurationException {
		
		boolean create = false;
		

		if (docsPath == null) {
			System.exit(1);
		}

		final Path docDir = Paths.get(docsPath);
		if (!Files.isReadable(docDir)) {
			System.out.println("Document directory '" + docDir.toAbsolutePath()
					+ "' does not exist or is not readable, please check the path");
			System.exit(1);
		}

		Date start = new Date();
		try {
//			System.out.println("Indexing to directory '" + indexPath + "'...");

			Directory dir = FSDirectory.open(Paths.get(indexPath));
			// Analyzer analyzer = new StandardAnalyzer();
			Analyzer analyzer = new EnglishAnalyzer();
			// Analyzer analyzer = new CustomAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			iwc.setSimilarity(new BM25Similarity());
			
		
				// Add new documents to an existing index:
				iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		

			// Optional: for better indexing performance, if you
			// are indexing many documents, increase the RAM
			// buffer. But if you do this, increase the max heap
			// size to the JVM (eg add -Xmx512m or -Xmx1g):
			//
			// iwc.setRAMBufferSizeMB(256.0);

			IndexWriter writer = new IndexWriter(dir, iwc);
			indexDocument(writer, docDir);

			// NOTE: if you want to maximize search performance,
			// you can optionally call forceMerge here. This can be
			// a terribly costly operation, so generally it's only
			// worth it when your index is relatively static (ie
			// you're done adding documents to it):
			//
			// writer.forceMerge(1);
			writer.close();

			Date end = new Date();
//			System.out.println(end.getTime() - start.getTime() + " total milliseconds");

		} catch (IOException e) {
			System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
		}
	}

	static void indexDocument(IndexWriter writer, Path file) throws IOException, SAXException, ParserConfigurationException {
		
		System.out.println("Indexing....");
		File folder = new File(file.toString() + "/");
		File[] listOfFiles = folder.listFiles();
//		String[] fieldName = { "TITLE", "BODY", "DATE" };
		int m = 1;
		int k=1;
		List<Document> docs = new ArrayList();
		for (File fileName : listOfFiles) {			
			Document doc = new Document();
			k++;
			Scanner s = new Scanner(new File(file.toString()+"/"+fileName.getName()));
			String s1 = s.useDelimiter("\\Z").next();
//			Map tagContent  = xmlParser.parse(fileName.getName(), fieldName, file);
			doc.add(new StringField("id", fileName.getName(), Field.Store.YES));
			doc.add(new TextField("content", s1, Field.Store.YES));
//			doc.add(new TextField("TITLE", tagContent.get("TITLE").toString(), Field.Store.YES));
//			doc.add(new TextField("BODY", tagContent.get("BODY").toString(), Field.Store.YES));
//			doc.add(new TextField("DATE", tagContent.get("DATE").toString(), Field.Store.YES));
			docs.add(doc);
		}
		
		 
		        writer.addDocuments(docs);
		     System.out.println("indexing done with "+ k);
		

	}
	
}
