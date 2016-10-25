package org.hibernate.tutorial.annotations;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import uk.co.neo9.apps.accounts.db.jdbc.TestDAO;
import uk.co.neo9.apps.accounts.db.jdbc.TestModel;

public class TestRunner {

	public static void main(String[] args) {
		
		String path = "./src/test/resources/application.xml";
		Resource resource = new FileSystemResource(path );
		BeanFactory factory = new XmlBeanFactory(resource);
		
		TestModel model = (TestModel) factory.getBean("modelInstance");
		
		System.out.println(model.getName());

		
		// proved the application.xml loads ok so now get the DAO
		TestDAO dao =  (TestDAO) factory.getBean("testDAO");
		List<TestModel> results = dao.getByName("Scooby");
		for (TestModel testModel : results) {
			System.out.println(testModel.getName());
		}
		
		
	}

}
