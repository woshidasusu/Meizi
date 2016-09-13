package coder.dasu.meizi.data.dao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import coder.dasu.meizi.data.bean.Meizi;

public class MeiziTest extends AbstractDaoTestLongPk<MeiziDao, Meizi> {

    public MeiziTest() {
        super(MeiziDao.class);
    }

    @Override
    protected Meizi createEntity(Long key) {
        Meizi entity = new Meizi();
        entity.setUnique_id(key);
        entity.setUsed(true);
        return entity;
    }

}
