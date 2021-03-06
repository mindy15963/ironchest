/*******************************************************************************
 * Copyright (c) 2012 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * <p>
 * Contributors:
 * cpw - initial API and implementation
 ******************************************************************************/
package com.progwml6.ironchest;

import com.progwml6.ironchest.client.ClientProxy;
import com.progwml6.ironchest.common.ServerProxy;
import com.progwml6.ironchest.common.ai.CatsSitOnChestsHandler;
import com.progwml6.ironchest.common.inventory.ChestContainerType;
import com.progwml6.ironchest.common.network.PacketHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = IronChest.MOD_ID)
public class IronChest
{
    public static final String MOD_ID = "ironchest";

    public static IronChest instance;

    public static ServerProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public IronChest()
    {
        instance = this;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
        MinecraftForge.EVENT_BUS.register(new CatsSitOnChestsHandler());
    }

    private void preInit(final FMLCommonSetupEvent event)
    {
        proxy.preInit();

        DistExecutor.runWhenOn(Dist.CLIENT, () -> ChestContainerType::registerScreenFactories);

        PacketHandler.register();
    }
}
