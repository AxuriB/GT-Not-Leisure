package com.science.gtnl.api.mixinHelper;

import net.minecraft.entity.Entity;

public interface ILeashedToEntity {

    void setLeashedToEntity(Entity entityIn, boolean sendAttachNotification);

    boolean getLeashed();

    Entity getLeashedToEntity();

    void updateLeashedState();

    void clearLeashed(boolean p_110160_1_, boolean p_110160_2_);
}
