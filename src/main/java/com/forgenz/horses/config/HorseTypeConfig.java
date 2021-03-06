/*
 * Copyright 2013 Michael McKnight. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and contributors and should not be interpreted as representing official policies,
 * either expressed or implied, of anybody else.
 */

package com.forgenz.horses.config;

import org.bukkit.configuration.ConfigurationSection;

import com.forgenz.forgecore.v1_0.ForgeCore;
import com.forgenz.forgecore.v1_0.bukkit.ForgePlugin;
import com.forgenz.forgecore.v1_0.util.BukkitConfigUtil;
import com.forgenz.horses.HorseType;

public class HorseTypeConfig implements ForgeCore
{
	private ForgePlugin plugin;
	
	public final HorseType type;
	public final String displayName;
	
	public final double horseHp;
	public final double horseMaxHp;
	public final double horseMaximumHpUpgrade;
	public final double speed;
	public final double jumpStrength;
	
	public final boolean protectFromDeletionOnDeath;
	
	// Economy related stuff
	public final double buyCost, wildClaimCost, healCost, hpUpgradeCost, renameCost;
	
	public HorseTypeConfig(ForgePlugin plugin, ConfigurationSection cfg, HorseType type)
	{
		this.plugin = plugin;
		this.type = type;
		
		displayName = BukkitConfigUtil.getAndSet(cfg, "DisplayName", String.class, type.toString());
		
		double horseHp = BukkitConfigUtil.getAndSet(cfg, "DefaultHealth", Number.class, 12.0).doubleValue();
		if (horseHp < 0)
		{
			horseHp = 12.0;
			BukkitConfigUtil.set(cfg, "DefaultHealth", horseHp);
			
		}
		this.horseHp = horseHp;
			
		double horseMaxHp = BukkitConfigUtil.getAndSet(cfg, "DefaultMaxHealth", Number.class, horseHp).doubleValue();
		if (horseMaxHp < 0)
		{
			horseMaxHp = horseHp;
			BukkitConfigUtil.set(cfg, "DefaultMaxHealth", horseMaxHp);
		}
		else if (horseHp > horseMaxHp)
		{
			horseMaxHp = horseHp;
			BukkitConfigUtil.set(cfg, "DefaultMaxHealth", horseMaxHp);
		}
		this.horseMaxHp = horseMaxHp;
		
		
		speed = BukkitConfigUtil.getAndSet(cfg, "Speed", Number.class, 0.225).doubleValue();
		jumpStrength = BukkitConfigUtil.getAndSet(cfg, "JumpStrength", Number.class, 0.7).doubleValue();
		
		horseMaximumHpUpgrade = BukkitConfigUtil.getAndSet(cfg, "MaxHpUpgrade", Number.class, 30.0).doubleValue();
		
		protectFromDeletionOnDeath = BukkitConfigUtil.getAndSet(cfg, "ProtectFromDeletionOnDeath", Boolean.class, false);
		
		// Only setup economy settings if economy is enabled
		if (getPlugin().getEconomy() != null)
		{
			buyCost = BukkitConfigUtil.getAndSet(cfg, "BuyCost", Number.class, 10.0).doubleValue();
			wildClaimCost = BukkitConfigUtil.getAndSet(cfg, "WildClaimCost", Number.class, 0.0).doubleValue();
			healCost = BukkitConfigUtil.getAndSet(cfg, "HealCost", Number.class, 10.0).doubleValue();
			hpUpgradeCost = BukkitConfigUtil.getAndSet(cfg, "HpUpgradeCost", Number.class, 10.0).doubleValue();
			renameCost = BukkitConfigUtil.getAndSet(cfg, "RenameCost", Number.class, 5.0).doubleValue();
		}
		else
		{
			buyCost = wildClaimCost = healCost = hpUpgradeCost = renameCost = 0.0;
		}
	}

	@Override
	public ForgePlugin getPlugin()
	{
		return plugin;
	}
}
