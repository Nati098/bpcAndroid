package com.example.threadsactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MainActivity extends AppCompatActivity {
    private LeftLeg t1;
    private RightLeg t2;

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

        public void setIsRunning(boolean state){
            this.isRunning = state;
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

        public void setIsRunning(boolean state){
            this.isRunning = state;
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
        this.t1 = new LeftLeg(lock);
        this.t2 = new RightLeg(lock);
        new Thread(t1).start();
        new Thread(t2).start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        t1.setIsRunning(false);
        t2.setIsRunning(false);

    }
}
