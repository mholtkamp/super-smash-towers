package com.me.td;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class FileManager
{
	
	private File file;
	private int[] stars;
	
	public FileManager()
	{
		file = new File("stars.txt");
		stars = read_stars();
	}
	
	public int[] read_stars()
	{
		int[] stars = new int[4];
		if (!file.exists())
		{
			try
			{
				FileWriter file_writer = new FileWriter(file);
				file_writer.write("0 0 0 0");
				file_writer.close();
			}
			catch (IOException e)
			{
				System.out.println(e.getMessage());
			}
		}
		String line = "";
		try
		{
			BufferedReader buffered_reader = new BufferedReader(new FileReader(file));
			line = buffered_reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
		for (int i = 0; i < 4; i++)
			stars[i] = line.charAt(i * 2) - '0';
		return stars;
	}
	
	public void update_stars(int level_index, int difficulty)
	{// difficulty: easy = 1 star, medium = 2 stars, hard = 3 stars
		stars[level_index] = Math.max(stars[level_index], difficulty);
		try
		{
			FileWriter file_writer = new FileWriter(file);
			String s = "";
			for (int i = 0; i < stars.length; i++)
				s = s + stars[i] + " ";
			file_writer.write(s);
			file_writer.close();
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void update_stars(String star_numbers)
	{
		try
		{
			FileWriter file_writer = new FileWriter(file);
			file_writer.write(star_numbers);
			file_writer.close();
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}

}
