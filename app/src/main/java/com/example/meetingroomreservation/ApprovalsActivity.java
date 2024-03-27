package com.example.meetingroomreservation;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List; // Change the import statement
import java.util.ArrayList;

public class ApprovalsActivity extends AppCompatActivity {

    private ListView listViewApprovals;
    private MyDBHelper dbHelper;
    private ApprovalAdapter approvalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approvals);

        listViewApprovals = findViewById(R.id.listViewApprovals);
        dbHelper = new MyDBHelper(this);

        // Fetch and display pending approvals
        displayPendingApprovals();
    }

    private void displayPendingApprovals() {
        List<Reservation> pendingApprovals = dbHelper.getPendingApprovals(); // Change the type

        // Initialize the custom adapter
        approvalAdapter = new ApprovalAdapter(getApplicationContext(), pendingApprovals);
        listViewApprovals.setAdapter(approvalAdapter);
    }
}
