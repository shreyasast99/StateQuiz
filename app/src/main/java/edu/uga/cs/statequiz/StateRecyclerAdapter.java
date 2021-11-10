package edu.uga.cs.statequiz;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * This is an adapter class for the RecyclerView to show all job leads.
 */
public class StateRecyclerAdapter extends RecyclerView.Adapter<StateRecyclerAdapter.JobLeadHolder> {

    public static final String DEBUG_TAG = "JobLeadRecyclerAdapter";

    private List<State> stateList;

    public StateRecyclerAdapter( List<State> jobLeadList ) {
        this.stateList = stateList;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    class JobLeadHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView score;
        TextView secondcity;
        TextView thirdcity;

        public JobLeadHolder(View itemView ) {
            super(itemView);

            date = (TextView) itemView.findViewById( R.id.date );
            score = (TextView) itemView.findViewById( R.id.score );
            //secondcity = (TextView) itemView.findViewById( R.id.secondcity );
            //thirdcity = (TextView) itemView.findViewById( R.id.thirdcity );
        }
    }

    @Override
    public JobLeadHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        // We need to make sure that all CardViews have the same, full width, allowed by the parent view.
        // This is a bit tricky, and we must provide the parent reference (the second param of inflate)
        // and false as the third parameter (don't attach to root).
        // Consequently, the parent view's (the RecyclerView) width will be used (match_parent).
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.card_result, parent, false );
        return new JobLeadHolder( view );
    }

    // This method fills in the values of a holder to show a JobLead.
    // The position parameter indicates the position on the list of jobs list.
    @Override
    public void onBindViewHolder( JobLeadHolder holder, int position ) {
        State state = stateList.get( position );

        Log.d( DEBUG_TAG, "onBindViewHolder: " + state );

        holder.date.setText( state.getState());
        holder.score.setText( state.getCapital() );
    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }
}