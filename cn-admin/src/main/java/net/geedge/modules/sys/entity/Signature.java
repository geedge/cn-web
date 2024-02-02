package net.geedge.modules.sys.entity;


import lombok.Data;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@Data
public class Signature implements Serializable{

	@XmlElement(name="DigestMethod")
	private String digestMethod;
	
	@XmlElement(name="DigestValue")
	private String digestValue;

	@XmlTransient
	public String getDigestMethod() {
		return digestMethod;
	}
	
	@XmlTransient
	public String getDigestValue() {
		return digestValue;
	}
	
	
}