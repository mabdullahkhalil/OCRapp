package com.something.mabdullahk.ocrapp;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class attendanceCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

private Activity mContext;
private List<userClass2> attendancesList;
private String type;

        attendanceCardAdapter(Activity mContext, List<userClass2> attendancesList,String type){
        this.mContext = mContext;
        this.attendancesList=attendancesList;
        this.type = type;

        }

@Override
public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.println("view type::::"+ viewType);
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_card, parent, false);
        return new attendancesCardViewHolder(mView);

        }



@Override
public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {




        ((attendancesCardViewHolder)holder).name.setText(attendancesList.get(position).getName());
        ((attendancesCardViewHolder)holder).cnic.setText(attendancesList.get(position).getCnic());
        ((attendancesCardViewHolder)holder).time.setText(attendancesList.get(position).getTime());
        if (type == "absent"){
            ((attendancesCardViewHolder)holder).attType.setBackgroundColor(Color.parseColor("#FF0000"));
        } else {
            ((attendancesCardViewHolder)holder).attType.setBackgroundColor(Color.parseColor("#32CD32"));

        }

        }

@Override
public int getItemCount() {
        return attendancesList.size();
        }

}


class attendancesCardViewHolder extends RecyclerView.ViewHolder{
    TextView name;
    TextView cnic;
    TextView time;
    LinearLayout attType;


    public attendancesCardViewHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.cardName);
        cnic = itemView.findViewById(R.id.cardCNIC);
        time = itemView.findViewById(R.id.cardAttendance);
        attType = itemView.findViewById(R.id.eventSideBar);

    }
}