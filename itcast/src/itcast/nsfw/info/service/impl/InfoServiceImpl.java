package itcast.nsfw.info.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import itcast.core.service.impl.BaseServiceImpl;
import itcast.nsfw.info.dao.InfoDao;
import itcast.nsfw.info.entity.Info;
import itcast.nsfw.info.service.InfoService;
@Service("infoService")
public class InfoServiceImpl extends BaseServiceImpl<Info> implements InfoService {

	private InfoDao infoDao;
	@Resource
	public void setInfoDao(InfoDao infoDao) {
		super.setBaseDao(infoDao);
		this.infoDao = infoDao;
	}
}
