package com.avalon.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sun.source.tree.WhileLoopTree;

public class ArrayThreadTest
{
	static ArrayList<Integer> list = new ArrayList<>();

	public static void main(String[] args)
	{
		Thread thread = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (true)
				{
					ArrayList<Integer> list1 = new ArrayList<Integer>();
					list1.add(new Random().nextInt(100));
					list = list1;
				}
				
			}
		});
		thread.start();

		Thread t1 = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (true)
				{
					for (Integer string : list)
					{
						System.out.println(string);
					}
				}

			}
		});

		Thread t2 = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (true)
				{
					for (Integer string : list)
					{
						System.out.println(string);
					}
				}

			}
		});

		t1.start();
		t2.start();
	}
}
