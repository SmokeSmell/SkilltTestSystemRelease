package com.gong.listener;

import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.gong.domain.ItemInfo;
import com.gong.helper.ItemParser;

public class ContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		ServletContext context = arg0.getServletContext();
		ItemParser parser = new ItemParser();
		String path3 = context.getRealPath("/WEB-INF/itemconfig.xml");
		parser.init(path3);
		HashMap<String, ItemInfo> itemInfos = parser.getConfigs();
		parser.clear();
		context.setAttribute("edit", itemInfos);
	}
}
