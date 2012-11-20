package esos.MobiBench;

import android.os.Handler;
import android.os.Message;

public class ProgressTrd extends Thread{
	private static int part_flag = 0;
	private int switch_flag= 7;
	private int count_flag = 0;	
	private boolean run_flag = true;
	private boolean wait = false;
	private Handler mHandler;
	
	private Message msg = null;
	
	ProgressTrd(Handler handler){
		mHandler = handler;
	}
	
	public void run(){
		switch(switch_flag){
		
			//p5, p6, p7���� ��
			case 0:
				count_flag = 0;
				msg = Message.obtain(mHandler, 1); 
				mHandler.sendMessage(msg);// progress bar�� max�� 23���� �Ѵ�.
				while(run_flag) {          
				//���� �ݺ��ϸ鼭 1�ʿ� 1�� ����
					synchronized(this){
						if(wait){
							try{
								wait();
							}catch(Exception e){
								//
							}
						}
					}
					try {

						Thread.sleep(1000); // 1�ʸ� �� ��, 
						++count_flag;
						// Part 5�� ��� - switch_flag�� 1�̴�. 
						switch(part_flag){
						case 0:
							if( (count_flag%23) == 0){
								msg = Message.obtain(mHandler, 2); 
								mHandler.sendMessage(msg);// progress�� 0���� �ʱ�ȭ ��Ų��. 
							}
							if( (count_flag % 920) == 0){
								//run_flag = false;
								msg = Message.obtain(mHandler, 3); 
								mHandler.sendMessage(msg);//progress bar�� 30���� �����Ͽ� part6�� ����ǵ��� �Ѵ�.
								msg = Message.obtain(mHandler, 6); 
								mHandler.sendMessage(msg);// Title bar�� part 6 ���� �ٲ��ش�.
								count_flag = 0;
								part_flag = 1;
							}
							break;
							

						}
						if(!wait){
							msg = Message.obtain(mHandler, 0); 
							mHandler.sendMessage(msg);// progress bar�� 1 ���� ��Ų��. 

						}
						
					}catch(InterruptedException e) {
						//
					}

				}
				msg = Message.obtain(mHandler, 8); 
				mHandler.sendMessage(msg);// progress�� 0���� �ʱ�ȭ ��Ų��. 
				break;
		
		}
	}
	
	public void pauseThread(){
		wait = true;
		synchronized(this){
			this.notify();
		}
	}
	
	public void resumeThread(){
		wait = false;
		synchronized(this){
			this.notify();
		}
	}
	
	public void stopThread(){
		run_flag = false;
		part_flag = 0;
		synchronized(this){
			this.notify();
		}
	}
	
	public void setTrd(boolean p5, boolean p6, boolean p7){
		if( p5 && p6 && p7){
			switch_flag = 0;
		}else if( p5 && p6){
			switch_flag = 1;
		}else if(p6 && p7){
			switch_flag = 2;
		}else if(p5 && p7){
			switch_flag = 3;
		}else if(p5){
			switch_flag = 4;
		}else if(p6){
			switch_flag = 5;
		}else if(p7){
			switch_flag = 6;
		}
	}

}
