package com.surgical.decision3.common.bean.preoperative;

import com.surgical.decision3.common.bean.scene.Scene;

import org.simpleframework.xml.Element;

public class PreoperativeResponse
{
	//Scene 
	@Element
    Scene scene;
	
	//is Scene event Success
	@Element
	Boolean isSceneEventSuccess;
	
	@Element(required=false)
	String sceneEventErrorMessage;
	
	@Element(required=false)
	PreoperativeResponseData preoperativeResponseData;
	
	public PreoperativeResponseData getPreoperativeResponseData()
	{
		return preoperativeResponseData;
	}
	
	public void setPreoperativeResponseData(
			PreoperativeResponseData preoperativeResponseData)
	{
		this.preoperativeResponseData = preoperativeResponseData;
	}

	public Scene getScene()
	{
		return scene;
	}

	public void setScene(Scene scene)
	{
		this.scene = scene;
	}

	public Boolean getIsSceneEventSuccess()
	{
		return isSceneEventSuccess;
	}

	public void setIsSceneEventSuccess(Boolean isSceneEventSuccess)
	{
		this.isSceneEventSuccess = isSceneEventSuccess;
	}

	public String getSceneEventErrorMessage()
	{
		return sceneEventErrorMessage;
	}

	public void setSceneEventErrorMessage(String sceneEventErrorMessage)
	{
		this.sceneEventErrorMessage = sceneEventErrorMessage;
	}

}
