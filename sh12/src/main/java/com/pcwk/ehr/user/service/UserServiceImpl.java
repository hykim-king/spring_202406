package com.pcwk.ehr.user.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;

@Service("userServiceImpl")
public class UserServiceImpl implements PLog, UserService {

	public static final int MIN_LOGINCOUNT_FOR_SILVER = 50; // 최소 로그인 횟수:50
	public static final int MIN_RECOMMEND_FOR_GOLD = 30; // 최소 추천 회수 : 30

	private MailSender mailSender;

	@Autowired
	UserMapper userMapper;//UserMapper,userDao

	public UserServiceImpl() {

	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}



	/**
	 * 최초 가입시 Level이 Null이면 BASIC
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int doSave(User user) throws SQLException {
		int flag = 0;

		if (null == user.getLevel()) {
			log.debug("user.getLevel():" + user.getLevel());
			user.setLevel(Level.BASIC);
		}

		flag = userMapper.doSave(user);
		log.debug("flag:" + flag);
		return flag;
	}


	// 등업 대상 추출
	public boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();

		switch (currentLevel) {
		case BASIC:
			return (user.getLogin() >= MIN_LOGINCOUNT_FOR_SILVER);
		case SILVER:
			return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
		case GOLD:
			return false;
		default:
			throw new IllegalArgumentException("Unknonw level");
		}

	}

	/**
	 * BASIC,SILVER대상으로 등업
	 * 
	 * @param user
	 * @throws SQLException
	 */
	protected void upgradeLevel(User user) throws SQLException {

		user.upgradeLevel();// 다음 Level
		userMapper.doUpdate(user);// 등업

		sendUpgradeEmail(user);// 등업자 email전송

	}

	private void sendUpgradeEmail(User user) {


		try {
			SimpleMailMessage message = new SimpleMailMessage();

			// 보내는 사람
			message.setFrom("jamesol@naver.com");

			// 받는 사람
			message.setTo(user.getEmail());

			// 제목
			message.setSubject("등업 안내");

			// 내용: 홍길동님의 등급이 GOLD로 등업 되었습니다.
			message.setText(user.getName() + "님의 등급이 " + user.getLevel() + "로 등업 되었습니다.");

			mailSender.send(message);
		} catch (Exception e) {
			log.debug("┌──────────────────────────────────────────┐");
			log.debug("│ sendUpgradeEmail()                       │"+e.getMessage());
			log.debug("└──────────────────────────────────────────┘");	
			e.printStackTrace();
		}
		
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ mail 전송                                                                         │"+user.getName());
		log.debug("└──────────────────────────────────────────┘");			
	}

	/**
	 * 전체 회원 등업 // 1. 전체 회원 조회 // 2. 등급: BASIC, SILVER, GOLD 등업 조건
	 * 
	 * // 1. BASIC : 최초 가입 // 2. SILVER : 가입 후 50회 이상 로그인 // 3. GOLD : SILVER이면서 30회
	 * // 3. 조건에 해당되는 회원 등업
	 * 
	 * @throws SQLException
	 */
	@Override
	public void upgradeLevelsTx() throws SQLException {

			// 1
			List<User> users = userMapper.getAll();
			// 2.
			for (User user : users) {

				// 등업 대상 선정
				if (true == canUpgradeLevel(user)) {
					upgradeLevel(user);
				}

			}

	}

}
