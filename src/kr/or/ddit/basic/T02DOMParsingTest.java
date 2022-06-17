package kr.or.ddit.basic;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class T02DOMParsingTest {
	public void parse() throws ParserConfigurationException, SAXException, IOException {
		
		// XML문서를 생성하기 위한 DocumentBuilder 객체 생성하기
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		
		File file = new File("./src/new_book.xml");
		
		 String url = file.toURI().toURL().toString();
		 System.out.println("url => " + url);
		 
		Document document = builder.parse(url);
		
		// DOM Document 객체로부터 루트 엘리먼트 가져오기
		Element root = document.getDocumentElement();
		System.out.println("root 엘리먼트 태그명 : " + root.getTagName());
		
		// 하위 엘리먼트 접근방법1 : getElementByTagName()메서드를 이용
		NodeList bookNodeList = root.getElementsByTagName("book");
		Node firstBookNode = bookNodeList.item(0); // 첫번째 항목
		Element fistBookElement = (Element) firstBookNode;
		
		// 속성 접근방법1 : 엘리먼트 객체의 getAttribute() 이용
		System.out.println("엘리먼트 객체의 getAttribute() 이용 => "
				+ fistBookElement.getAttribute("isbn"));
		
		// 속성 접근방법2 : 노드객체의 getAttributes() 이용(속성노드 접근)
		NamedNodeMap nodeMap = firstBookNode.getAttributes();
		System.out.println("노드객체의 getAttributes() 이용 => "
				+ nodeMap.getNamedItem("isbn").getNodeValue());
		
		// 하위 엘리먼트 접근방법2 : getChildNodes() 이용
		NodeList firstBookChildNodeList = firstBookNode.getChildNodes();
		
		// 엔터키에 해당하는 부분이 읽힐 수 있으므로, getChildNodes()보다
		// getElementsByTagName()을 이용해 접근하는 것이 좋다.
		Node titleNode = firstBookChildNodeList.item(0);
		Element titleElement = (Element) titleNode;
		System.out.println("titleElement.getTagName() => " 
					+ titleElement.getTagName());
		System.out.println("titleElement.getTextContent() => " 
					+ titleElement.getTextContent());
	
		// 전체 출력하기
		// 속성값 : isbn, kind
		// 엘리먼트 텍스트값 : title, author, price
		System.out.println("---------------------------------------------------");
		System.out.printf("%8s %8s %15s %10s %8s\n", 
							"ISBN", "분류","제목","저자","가격");
		System.out.println("---------------------------------------------------");
		
		for(int i=0; i < bookNodeList.getLength(); i++) {
			Node bookNode = bookNodeList.item(i);
			Element element = (Element) bookNode;
			
			String isbn = element.getAttribute("isbn");
			String kind = element.getAttribute("kind");
			String title = element.getElementsByTagName("title").item(0)
					.getTextContent().trim();
			String author = element.getElementsByTagName("author").item(0)
					.getTextContent().trim();
			String price = element.getElementsByTagName("price").item(0)
					.getTextContent().trim();
			
			String str = String.format("%8s %8s %15s %10s %8s", 
					isbn, kind, title, author, price);
			System.out.println(str);
		}
		
		
		
		
		
		
		
		
	}
	
	public static void main(String[] args) throws Exception {
		new T02DOMParsingTest().parse();
	}
}
