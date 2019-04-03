package com.example.threadsactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MainActivity extends AppCompatActivity {

    private class LeftLeg implements Runnable {
        private boolean isRunning = true;

        Lock lock;

        LeftLeg(ReentrantLock lo){
            this.lock = lo;
        }

        @Override
        public void run() {
            while (isRunning){

                lock.lock();
                System.out.println("Left step");

                lock.unlock();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {}

            }
        }
    }

    private class RightLeg implements Runnable {
        private boolean isRunning = true;

        Lock lock;

        RightLeg(ReentrantLock lo){
            this.lock = lo;
        }

        @Override
        public void run() {
            while (isRunning){

                lock.lock();
                System.out.println("Right step");

                lock.unlock();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {}

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart() {
        super.onStart();

        ReentrantLock lock = new ReentrantLock();
        new Thread(new LeftLeg(lock)).start();
        new Thread(new RightLeg(lock)).start();

    }
}


/*
public class MyThread extends Thread {

    static Lock lock = new ReentrantLock();
    private String strVoice;

    private void setVoice(String str) {
        this.strVoice = str;
    }

    MyThread(String str) {
        this.setVoice(str);
    }

    @Override
    public void run() {
        for (;;) { // бесконечные цикл
            System.out.println(this.strVoice);
            try {
                MyThread.sleep(1000);// чтобы спорили медленнее
            } catch (InterruptedException ex) {
                // Logger.getLogger(ChickenVoice.class.getName()).log(Level.SEVERE, null, ex);
            }
            lock.lock();
            lock.unlock();
        }
    }
}
 */