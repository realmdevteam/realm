package mods.realm;

import java.io.File;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class RealmConfiguration extends Configuration
{

	public RealmConfiguration(File file)
	{
		super(file);
	}

	@Override
	public void save()
	{
		super.save();
	}

}