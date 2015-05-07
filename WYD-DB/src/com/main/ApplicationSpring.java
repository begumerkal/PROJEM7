package com.main;

import java.util.Date;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mongo.entity.NameEntity;
import com.mongo.entity.UserEntity;
import com.mongo.impl.UserDao;

public class ApplicationSpring {

	private static ConfigurableApplicationContext context;
	public static void main(String[] args) {

		System.out.println("Bootstrapping HelloMongo");
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserDao  userDao = context.getBean(UserDao.class);
		
		
		NameEntity ne = new NameEntity();
		ne.setNickname("nickname");
		ne.setUsername("username");
		
		UserEntity entity1 = new UserEntity();
//		entity1.setId(1);
		entity1.setAge(111);
		entity1.setName(ne);
		entity1.setBirth(new Date());
		entity1.setPassword("asdfasdf");
		entity1.setRegionName("北京");
		entity1.setWorks(2);
		
//		userDao.insert(entity1);
		
		userDao.save(entity1);
 
//
//		List<UserEntity> list = userDao.findList(0, 10);
//		for (UserEntity e : list) {
//			System.out.println("all - id=" + e.getId() + ", age=" + e.getAge() + ", password=" + e.getPassword() + ", regionName="
//					+ e.getRegionName() + ", special=" + Arrays.toString(e.getSpecial()) + ", name=" + e.getName().getUsername() + "-"
//					+ e.getName().getNickname() + ", birth=" + e.getBirth());
//		}
//
//		list = userDao.findListByAge(1);
//		for (UserEntity e : list) {
//			System.out.println("age=1 - id=" + e.getId() + ", age=" + e.getAge() + ", password=" + e.getPassword() + ", regionName="
//					+ e.getRegionName() + ", special=" + Arrays.toString(e.getSpecial()) + ", name=" + e.getName().getUsername() + "-"
//					+ e.getName().getNickname() + ", birth=" + e.getBirth());
//		}
//
//		UserEntity e = userDao.findOne("1");
//		System.out.println("id=1 - id=" + e.getId() + ", age=" + e.getAge() + ", password=" + e.getPassword() + ", regionName="
//				+ e.getRegionName() + ", special=" + Arrays.toString(e.getSpecial()) + ", name=" + e.getName().getUsername() + "-"
//				+ e.getName().getNickname() + ", birth=" + e.getBirth());
//
//		e = userDao.findOneByUsername("limingnihao");
//		System.out.println("username=limingnihao - id=" + e.getId() + ", age=" + e.getAge() + ", password=" + e.getPassword()
//				+ ", regionName=" + e.getRegionName() + ", special=" + Arrays.toString(e.getSpecial()) + ", name="
//				+ e.getName().getUsername() + "-" + e.getName().getNickname() + ", birth=" + e.getBirth());
//
//		System.out.println("DONE!");
	}

}
