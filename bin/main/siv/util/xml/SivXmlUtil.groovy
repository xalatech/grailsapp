package siv.util.xml

import java.io.PrintWriter;
import java.io.StringWriter;
import grails.converters.*
import groovy.util.XmlNodePrinter;
import groovy.util.XmlParser;

class SivXmlUtil {

	/**
	 * Metode for aa formatere xml paa et pent format.
	 * 
	 * @param xml
	 * @return
	 */
	public static String prettyPrintXml( String xml ) {
		def stringWriter = new StringWriter()
		def node = new XmlParser().parseText( xml )
		new XmlNodePrinter( new PrintWriter(stringWriter)).print(node)
		return stringWriter.toString()
	}
}
