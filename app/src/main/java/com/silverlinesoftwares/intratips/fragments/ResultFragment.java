package com.silverlinesoftwares.intratips.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.silverlinesoftwares.intratips.R;
import com.silverlinesoftwares.intratips.adapters.ResultAdapter;
import com.silverlinesoftwares.intratips.listeners.ResultListener;
import com.silverlinesoftwares.intratips.models.ResultModel;
import com.silverlinesoftwares.intratips.tasks.ResultTask;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class ResultFragment extends Fragment  implements ResultListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView listView;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext=null;
    }

    public ResultFragment() {
        // Required empty public constructor
    }

    public static ResultFragment newInstance(String param1, String param2) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    ProgressBar progressBar;
    TextView  only_profit_text;
    TextView only_loss_text,accuracy;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView=view.findViewById(R.id.listview);
        progressBar=view.findViewById(R.id.progress);
        only_loss_text=view.findViewById(R.id.loss_amount);
        only_profit_text=view.findViewById(R.id.profit_amount);
        accuracy=view.findViewById(R.id.accuracy);
        final SwipeRefreshLayout pullToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.pullToRefresh);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                FragmentTransaction ft = null;
                if (getFragmentManager() != null) {
                    ft = getFragmentManager().beginTransaction();
                    ft.detach(ResultFragment.this).attach(ResultFragment.this).commit();
                    pullToRefresh.setRefreshing(false);
                }
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getChildAt(0) != null) {
                    pullToRefresh.setEnabled(listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0);
                }
            }
        });


        ResultTask resultTask=new ResultTask(ResultFragment.this);
        resultTask.execute();
    }

    @Override
    public void onSucess(List<ResultModel> data) {
        progressBar.setVisibility(View.GONE);
        if(mContext != null) {
            ResultAdapter resultAdapter = new ResultAdapter(mContext, data);
            listView.setAdapter(resultAdapter);
            BuildProfit(data);
        }

    }

    @Override
    public void onFailed(String msg) {
        if(progressBar!=null) {
            progressBar.setVisibility(View.GONE);
        }

        if(getContext()!=null) {
            Toast.makeText(getContext(), "" + msg, Toast.LENGTH_SHORT).show();
        }
    }

    public void BuildProfit(List<ResultModel> resultModels){

        double final_profit=0.0;
        double final_loss=0.0;
        for (ResultModel equityModel:resultModels) {
            int amount=100000;
            String[] price_Range = equityModel.getBuy_price().split("-");
            Log.d("Crash",equityModel.getSymbol()+" "+equityModel.getDatetime());

            double amt_1=Double.parseDouble(price_Range[0]);
            double amt_2=Double.parseDouble(price_Range[1]);
            double avg_amt=(amt_1+amt_2)/2;

            int quantity= (int) (amount/avg_amt);
            double only_profit = 0.0;
            double only_loss=0.0;

            if (equityModel.getStop_loss_end().contains("LOSS")) {
                if (equityModel.getBuy_text().contains("SELL")) {
                    only_loss = quantity * (Double.parseDouble(equityModel.getStop_loss()) - avg_amt);

                } else {
                    only_loss = quantity * (avg_amt - Double.parseDouble(equityModel.getStop_loss()));

                }
                final_loss=final_loss+only_loss;
            } else {
                if (equityModel.getAchieved3().contains("Done")) {
                    if (equityModel.getBuy_text().contains("SELL")) {
                        only_profit = quantity * (avg_amt - Double.parseDouble(equityModel.getBuy3()));
                        ////  is_loss=true;
                    } else {
                        only_profit = quantity * (Double.parseDouble(equityModel.getBuy3()) - avg_amt);
                        //  is_loss=true;
                    }

                } else if (equityModel.getAchieved2().contains("Done")) {
                    if (equityModel.getBuy_text().contains("SELL")) {
                        only_profit = quantity * (avg_amt - Double.parseDouble(equityModel.getBuy2()));
                        //  is_loss=true;
                    } else {
                        only_profit = quantity * (Double.parseDouble(equityModel.getBuy2()) - avg_amt);
                        //  is_loss=true;
                    }
                } else if (equityModel.getAchieved1().contains("Done")) {
                    if (equityModel.getBuy_text().contains("SELL")) {
                        only_profit = quantity * (avg_amt - Double.parseDouble(equityModel.getBuy1()));
                        //// is_loss=true;
                    } else {
                        only_profit = quantity * (Double.parseDouble(equityModel.getBuy1()) - avg_amt);
                        //   is_loss=true;
                    }
                } else {
                    only_profit = 0.0;
                }

                final_profit=final_profit+only_profit;
            }
        }

        double accu=final_profit+final_loss;
        double accu1=(final_profit*100)/accu;
        double roundOff = Math.round(accu1 * 100.0) / 100.0;
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        only_profit_text.setText(""+formatter.format(final_profit));
        only_loss_text.setText(""+formatter.format(final_loss));
        accuracy.setText(""+roundOff+" %");
    }
}
