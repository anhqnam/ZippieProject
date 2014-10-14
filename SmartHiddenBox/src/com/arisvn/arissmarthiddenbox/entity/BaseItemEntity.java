/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2014 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.entity - BaseItemEntity.java
 * Date create: 9:39:17 AM - Feb 20, 2014 - 2014
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.entity;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class BaseItemEntity.
 */
public class BaseItemEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The check. */
    private boolean check;

    /**
     * @return the check
     */
    public boolean isCheck() {
        return check;
    }

    /**
     * @param check the check to set
     */
    public void setCheck(boolean check) {
        this.check = check;
    }

}
