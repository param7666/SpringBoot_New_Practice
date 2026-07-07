package com.tcs.sbeans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Hotel {

	@Value("12345")
	private Integer hotelId;
	
	@Value("${hotel.name}")
	private String hotelName;
	
	@Value("${hotel.address}")
	private String hotelAddress;
	
	@Value("${hotel.contactNo}")
	private Long hotelContact;
	
	@Value("#{${menu.dosarate} + ${menu.poharate}}")
	private Float totalBill;
	
	@Value("${os.name}")
	private String osName;
	
	@Value("${user.name}")
	private String username;
	
	@Value("${Path}")
	private String pathData;

	@Override
	public String toString() {
		return "Hotel [hotelId=" + hotelId + ", hotelName=" + hotelName + ", hotelAddress=" + hotelAddress
				+ ", hotelContact=" + hotelContact + ", totalBill=" + totalBill + ", osName=" + osName + ", username="
				+ username + ", pathData=" + pathData + "]";
	}
	
	
	
}
