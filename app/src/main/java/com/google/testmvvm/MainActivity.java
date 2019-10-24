package com.google.testmvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;

import com.google.testmvvm.Database.AppDataBase;
import com.google.testmvvm.Database.TaskEntry;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.ItemClickListener {

    // Constant for logging
    private static final String TAG = MainActivity.class.getSimpleName();
    // Member variables for the adapter and RecyclerView
    private RecyclerView mRecyclerView;
    private TaskAdapter mAdapter;
    public AppDataBase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the RecyclerView to its corresponding view
        mRecyclerView = findViewById(R.id.recyclerViewTask);

        // Set the layout for the RecyclerView to be a linear layout, which measures and
        // positions items within a RecyclerView into a linear list
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new TaskAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        //to put dividers between items in recyclerView
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);


        /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
              AppExcuters.getInstance().diskIo().execute(new Runnable() {
                  @Override
                  public void run() {
                      int position=viewHolder.getAdapterPosition();
                      List<TaskEntry>entries=mAdapter.getTasks();
                      mDb.taskDao().DeleteTask(entries.get(position));

                  }
              });
            }
        }).attachToRecyclerView(mRecyclerView);

        /*
         Set the Floating Action Button (FAB) to its corresponding View.
         Attach an OnClickListener to it, so that when it's clicked, a new intent will be created
         to launch the AddTaskActivity.
         */
        FloatingActionButton fabButton = findViewById(R.id.fab);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to start an AddTaskActivity
                Intent addTaskIntent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(addTaskIntent);
            }
        });
     mDb=AppDataBase.getInstance(getApplicationContext());
        retreviteTask();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    private void retreviteTask(){
        MainViewModel viewModel= ViewModelProviders.of(this).get(MainViewModel.class);
//method observe  to observe database list when changes
        //(viewModel.getTasks(). ..... returns LiveData<List<TaskEntry>>)......
           viewModel.getTasks().observe(this, new Observer<List<TaskEntry>>() {
               @Override
               public void onChanged(@Nullable List<TaskEntry> taskEntries) {
                   Log.d(TAG, "onChanged: Updating list of tasks from liveData into ViewModel..");
                   mAdapter.setTasks(taskEntries);
               }
           });
    }
  /*  private void retreviteTask(){
        AppExcuters.getInstance().diskIo().execute(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    final List<TaskEntry> taskEntries = mDb.taskDao().loadAllTask();

                    @Override
                    public void run() {

                        mAdapter.setTasks(taskEntries);
                    }
                });
            }
            });
    }*/

    @Override
    public void onItemClickListener(int itemId) {
      Intent intent=new Intent(MainActivity.this,AddTaskActivity.class);
      intent.putExtra(AddTaskActivity.EXTRA_TASK_ID,itemId);
      startActivity(intent);
    }
}
