package com.example.mainactivity.debounced;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class DebouncedClickListener implements View.OnClickListener {
    private static final String TAG = "DebouncedClickListener";

    private long lastClickTime ;

    private static final long CLICK_INTERVAL = 2000;
    private RecyclerView.ViewHolder viewHolder;

    public DebouncedClickListener(){    }

    public DebouncedClickListener(RecyclerView.ViewHolder vH){
        this.viewHolder = vH;
    }

    DebouncedClickListener getInstance(RecyclerView.ViewHolder viewHolder){
        if (this.viewHolder == null){
            this.viewHolder = viewHolder;
        }
        return this;
    }

    public abstract void onItemClick(View v);

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < CLICK_INTERVAL) {
            Log.i(TAG, Integer.toHexString(System.identityHashCode(v))+": less than 2 secs"
                    +"; position = "+viewHolder.getAdapterPosition()
                    +"; from last time (millisecs):"+(currentTime - lastClickTime) );
            return;
        }

        Log.i(TAG, Integer.toHexString(System.identityHashCode(v))+": everything is okay"
                +"; position = "+viewHolder.getAdapterPosition()
                +"; from last time (millisecs):"+(currentTime - lastClickTime));

        lastClickTime = currentTime;
        onItemClick(v);



    }
}



/* ВАРИАНТ 1 (ЧЕРЕЗ lastTime)
        private static final String TAG = "DebouncedClickListener";

//    public long getLastClickTime() {
//        return lastClickTime;
//    }

        private long lastClickTime ;

        private static final long CLICK_INTERVAL = 2000;
        private RecyclerView.ViewHolder viewHolder;

        DebouncedClickListener(long lastClickTime, RecyclerView.ViewHolder viewHolder){
            this.lastClickTime = lastClickTime;
            this.viewHolder = viewHolder;
        }

        public abstract void onItemClick(View v);
//        public abstract void setLastClickTime(long newTime);

        @Override
        public void onClick(View v) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime < CLICK_INTERVAL) {
                Log.i(TAG, Integer.toHexString(System.identityHashCode(v))+": less than 2 secs"
                        +"; position = "+viewHolder.getAdapterPosition()
                        +"; from last time (millisecs):"+(currentTime - lastClickTime) );
                return;
            }

            Log.i(TAG, Integer.toHexString(System.identityHashCode(v))+": everything is okay"
                    +"; position = "+viewHolder.getAdapterPosition()
                    +"; from last time (millisecs):"+(currentTime - lastClickTime));

            lastClickTime = currentTime;
            onItemClick(v);



        }
* */

/* ВАРИАНТ 2
    private static final String TAG = "DebouncedClickListener";

    private long lastClickTime ;
    private static final long CLICK_INTERVAL = 2000;

    private RecyclerView.ViewHolder viewHolder = null;
    private int position = -1;


    DebouncedClickListener(){}

//        private static View singleton = null;
//        public void setSingletonNull(){
//            singleton = null;
//        }

    public DebouncedClickListener getDebouncedClickListener(RecyclerView.ViewHolder vH){
        if (viewHolder == null){
            viewHolder = vH;
            Log.i("DebouncedClickListener",
                    Integer.toHexString(System.identityHashCode(viewHolder.itemView))+
                            ": created new object DebouncedClickListener for it");
        }

        Log.i("DebouncedClickListener",
                Integer.toHexString(System.identityHashCode(viewHolder.itemView))+
                        ": object DebouncedClickListener has already existed");

        return this;
    }

    public DebouncedClickListener getDebouncedClickListener(int position){
        if (this.position == -1){
            this.position = position;
            Log.i("DebouncedClickListener",
                    "Put position into debounced: "+this.position);
        }

        Log.i("DebouncedClickListener",
                "Trying to put position into debounced. Object DebouncedClickListener has already existed");

        return this;
    }

    public abstract void onItemClick(View v, int position);

    @Override
    public void onClick(View v) {
        //if (singleton == null){
        //    singleton = v;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < CLICK_INTERVAL) {
            Log.i(TAG, Integer.toHexString(System.identityHashCode(v))+": less than 2 secs"
                    +"; position = "+viewHolder.getAdapterPosition()
                    +"; from last time (millisecs):"+(currentTime - lastClickTime) );
            return;
        }

        Log.i(TAG, Integer.toHexString(System.identityHashCode(v))+": everything is okay"
                +"; position = "+viewHolder.getAdapterPosition()
                +"; from last time (millisecs):"+(currentTime - lastClickTime));

        lastClickTime = currentTime;

//                Log.i(TAG,
//                    Integer.toHexString(System.identityHashCode(v))+
//                            ": onItemClick"
//                            +"; position = "+viewHolder.getAdapterPosition());

        if (viewHolder != null) {
            onItemClick(v, viewHolder.getAdapterPosition());
        } else if (position != -1){
            onItemClick(v, position);
        }
        else{
            Log.i(TAG,
                    "onClick, viewHolder & position are bad");
            onItemClick(v, -1);
        }
        //}

    }
* */