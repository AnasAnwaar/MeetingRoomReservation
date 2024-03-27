package com.example.meetingroomreservation;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ApprovalAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Reservation> reservations;
    private MyDBHelper dbHelper;

    public ApprovalAdapter(Context context, List<Reservation> reservations) {
        this.context = context;
        this.reservations = (ArrayList<Reservation>) reservations;
        dbHelper = new MyDBHelper(context);
    }

    @Override
    public int getCount() {
        return reservations.size();
    }

    @Override
    public Object getItem(int position) {
        return reservations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_approval, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewReservedFor = convertView.findViewById(R.id.textViewReservedFor);
            viewHolder.textViewRoomNo = convertView.findViewById(R.id.textViewRoomNo);
            viewHolder.textViewMeetingType = convertView.findViewById(R.id.textViewMeetingType);
            viewHolder.textViewAgenda = convertView.findViewById(R.id.textViewAgenda);
            viewHolder.textViewDate = convertView.findViewById(R.id.textViewDate);
            viewHolder.textViewTiming = convertView.findViewById(R.id.textViewTiming);
            viewHolder.buttonApprove = convertView.findViewById(R.id.buttonApprove);
            viewHolder.buttonDecline = convertView.findViewById(R.id.buttonDecline);
            viewHolder.buttonEdit = convertView.findViewById(R.id.buttonEdit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Reservation reservation = reservations.get(position);
        viewHolder.textViewReservedFor.setText(reservation.getReservedFor());
        viewHolder.textViewRoomNo.setText(reservation.getRoomNo());
        viewHolder.textViewMeetingType.setText(reservation.getMeetingType());
        viewHolder.textViewAgenda.setText(reservation.getAgenda());
        viewHolder.textViewDate.setText(reservation.getDate());
        viewHolder.textViewTiming.setText(reservation.getTiming());

        viewHolder.buttonApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveReservation(position);
            }
        });

        viewHolder.buttonDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                declineReservation(position);
            }
        });

        viewHolder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editReservation(position);
            }
        });

        return convertView;
    }

    private void approveReservation(int position) {
        Reservation reservation = reservations.get(position);
        dbHelper.updateReservationStatus(reservation.getId(), "Approved");
        // Notify the user that their reservation has been approved
        NotificationHandler.showNotification(context, "Reservation Approved", "Your reservation has been approved.");
        // Remove the reservation from the list and update the adapter
        reservations.remove(position);
        notifyDataSetChanged();
    }

    private void declineReservation(int position) {
        Reservation reservation = reservations.get(position);
        dbHelper.updateReservationStatus(reservation.getId(), "Declined");
        // Notify the user that their reservation has been declined
        NotificationHandler.showNotification(context, "Reservation Declined", "Your reservation has been declined.");
        // Remove the reservation from the list and update the adapter
        reservations.remove(position);
        notifyDataSetChanged();
    }
    public void refreshReservationList() {
        notifyDataSetChanged();
    }


    private void editReservation(int position) {
        // Retrieve the selected reservation based on the position
        final Reservation reservation = reservations.get(position);

        // Inflate the layout for the edit dialog
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_reservation, null);

        // Find views within the dialog layout
        EditText editTextReservedFor = dialogView.findViewById(R.id.editTextReservedFor);
        EditText editTextMeetingAgenda = dialogView.findViewById(R.id.editTextMeetingAgenda);
        EditText editTextDate = dialogView.findViewById(R.id.editTextDate);
        EditText editTextTiming = dialogView.findViewById(R.id.editTextTiming);
        Button buttonSave = dialogView.findViewById(R.id.buttonSave);

        // Populate the edit dialog with reservation details
        editTextReservedFor.setText(reservation.getReservedFor());
        editTextMeetingAgenda.setText(reservation.getAgenda());
        editTextDate.setText(reservation.getDate());
        editTextTiming.setText(reservation.getTiming());

        // Create and show the edit dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        // Set click listener for the save button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve updated reservation details from edit dialog
                String updatedReservedFor = editTextReservedFor.getText().toString().trim();
                String updatedMeetingAgenda = editTextMeetingAgenda.getText().toString().trim();
                String updatedDate = editTextDate.getText().toString().trim();
                String updatedTiming = editTextTiming.getText().toString().trim();

                // Update the reservation details in the database
                dbHelper.updateReservationDetails(reservation.getId(), updatedReservedFor, updatedMeetingAgenda, updatedDate, updatedTiming);

                // Notify the user that the reservation has been updated
                Toast.makeText(context, "Reservation updated successfully", Toast.LENGTH_SHORT).show();

                // Dismiss the edit dialog
                dialog.dismiss();

                // Refresh the list to reflect the changes
                refreshReservationList();
            }
        });
    }

    static class ViewHolder {
        TextView textViewReservedFor, textViewRoomNo, textViewMeetingType, textViewAgenda, textViewDate, textViewTiming;
        Button buttonApprove, buttonDecline, buttonEdit;
    }
}
