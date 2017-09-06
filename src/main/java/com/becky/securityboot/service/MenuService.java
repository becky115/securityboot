package com.becky.securityboot.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.becky.securityboot.mappers.MenuMapper;

@Service
public class MenuService {
	
	private static final Logger logger = LoggerFactory.getLogger(MenuService.class);
	
	@Autowired
	private MenuMapper menuMapper;
	
	private static Map<Integer, Object> menuList;
	private static Map<Object, List<Object>> authList;
	
	private static String MENU_DEPTH_1 = "menuDepth1_";
	private static String MENU_DEPTH_2 = "menuDepth2_";
	private static String MENU_DEPTH_3 = "menuDepth3_";
	
	@PostConstruct
	public void init() {
		logger.debug("menu init.......");
		List<Map<?, ?>> allMenuList = this.selectListByGroup(null);
		convertMenu(allMenuList);
		System.out.println(allMenuList);
		
		List<Map<?, ?>> notAuthMenuList = this.selectMenuNotAuth();//권한 없는 메뉴 
		convertAuthMenu(notAuthMenuList);
	}
	
	private void convertAuthMenu(List<Map<?, ?>> allMenuList) {
//		System.out.println(allMenuList);
//		System.out.println(allMenuList.stream());
		authList = allMenuList.stream()
				.collect(Collectors.groupingBy(x -> x.get("group_seq"), Collectors.mapping(x -> x.get("menu_url"), Collectors.toList())));
		System.out.println("authList  " + authList);
	}

	private void convertMenu(List<Map<?, ?>> allMenuList) {
		Map<Object, List<Map<?, ?>>> menuListByGroup = allMenuList.stream().collect(Collectors.groupingBy(x -> x.get("group_seq")));
		menuList = new LinkedHashMap<>();
		menuListByGroup.forEach((k, menu) -> {
			menuList.put((Integer) k, getMenu(menu));
		});
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Map<?, ?>> getMenu(List<Map<?, ?>> menu){
		Map<String, Map<?, ?>> resultMenu = new LinkedHashMap<>();
		
		for(int i=0; i<menu.size(); i++){
			Map<?, ?> menuInfo = menu.get(i);
			Integer menuSeq = Integer.parseInt(menuInfo.get("menu_seq").toString());
			Integer menuTopParent = Integer.parseInt(menuInfo.get("menu_top_parent").toString());
			Integer menuSubParent = Integer.parseInt(menuInfo.get("menu_sub_parent").toString());
			
			Map<String, Object> menuDepth1 = (Map<String, Object>) resultMenu.get(MENU_DEPTH_1 + menuSeq);
			if(menuTopParent == 0 && menuSubParent == 0){//depth1
				resultMenu.put(MENU_DEPTH_1 + menuSeq, menuInfo);
			}else{
				menuDepth1 = (Map<String, Object>) resultMenu.get(MENU_DEPTH_1 + menuTopParent);
				if(menuDepth1 != null){
					Map<String, Object> menuDepth2 = (Map<String, Object>) menuDepth1.get(MENU_DEPTH_2 + menuTopParent);
					if(menuDepth2 != null){
						if(menuSubParent == 0){//depth2
							List<Map<?, ?>> list = (List<Map<?, ?>>) menuDepth2.get("list");
							if(list != null){
								list.add(menuInfo);
							}
							
						}else{
							Map<?, ?> menuDepth3 = (Map<?, ?>) menuDepth2.get(MENU_DEPTH_3 + menuTopParent +"_"+menuSubParent);
							if(menuDepth3 != null){
								List<Map<?, ?>> list = (List<Map<?, ?>>) menuDepth3.get("list");
								if(list != null){
									list.add(menuInfo);
								}
							}else{
								Map<String, Object> emptyMenu = new HashMap<>();
								List<Map<?, ?>> list = new ArrayList<>();
								list.add(menuInfo);
								emptyMenu.put("list", list);
								menuDepth2.put(MENU_DEPTH_3 + menuTopParent + "_" + menuSubParent, emptyMenu);
							}
						}
					}else{
						Map<String, Object> emptyMenu = new HashMap<>();
						List<Map<?, ?>> list = new ArrayList<>();
						list.add(menuInfo);
						emptyMenu.put("list", list);
						menuDepth1.put(MENU_DEPTH_2 + menuTopParent, emptyMenu);
					}
				}
			
			}
		}
		
		return resultMenu;
		
	}

	public List<Map<?, ?>> selectListByGroup(Integer groupSeq){
		return menuMapper.selectListByGroup(groupSeq);
	}
	
	public Map<?, ?> getMenuListByGroup(Integer groupSeq){
		logger.debug("group seq: " + groupSeq);
		return menuList.get(groupSeq) == null ? null:(Map<?, ?>) menuList.get(groupSeq);
	
	}

	public static Map<Integer, Object> getMenuList() {
		return menuList;
	}
	
	//authList 권한 없는 리스트
	public boolean checkAuth(Integer groupSeq, String menuUrl){
		System.out.println("check");
		logger.info("checkAuth menuUrl "+groupSeq+","+ menuUrl);
		List<Object> menuUrlList = authList.get(groupSeq);
		System.out.println("groupSeq" + groupSeq +" menuUrl"+menuUrl);
		System.out.println(menuUrlList);
		if(menuUrlList != null){
			return !menuUrlList.contains(menuUrl);
		}
		return true;
	}

	public static void setMenuList(Map<Integer, Object> menuList) {
		MenuService.menuList = menuList;
	}
	
	private List<Map<?, ?>> selectMenuNotAuth(){
		List<Map<?, ?>> notAuthMenuList = menuMapper.selectMenuNotAuth();
		return notAuthMenuList;
	}
	


}