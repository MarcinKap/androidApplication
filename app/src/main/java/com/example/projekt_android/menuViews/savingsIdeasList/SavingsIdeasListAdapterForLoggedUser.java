package com.example.projekt_android.menuViews.savingsIdeasList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_android.MainActivity;
import com.example.projekt_android.R;
import com.example.projekt_android.api.ApiUtils;
import com.example.projekt_android.database.entity.UserEntity;
import com.example.projekt_android.model.SavingsIdea;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavingsIdeasListAdapterForLoggedUser extends RecyclerView.Adapter<SavingsIdeasListAdapterForLoggedUser.SavingsIdeaViewHolder> {

    private List<SavingsIdea> savingsIdeaList;
    private Context context;
    private UserEntity loggedUser;


    public SavingsIdeasListAdapterForLoggedUser(List<SavingsIdea> savingsIdeaList, Context context, UserEntity userEntity) {
        this.context = context;
        this.savingsIdeaList = savingsIdeaList;
        this.loggedUser = userEntity;
    }

    @NonNull
    @Override
    public SavingsIdeaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_savings_idea, parent, false);

        return new SavingsIdeaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavingsIdeaViewHolder holder, int position) {
        SavingsIdea savingsIdea = savingsIdeaList.get(position);

        if (savingsIdea == null) {
            return;
        }
        holder.bind(holder.itemView.getContext(), savingsIdea);
    }

    @Override
    public int getItemCount() {
        return savingsIdeaList.size();
    }


    class SavingsIdeaViewHolder extends RecyclerView.ViewHolder {
        private TextView savingsIdeaTitle;
        private TextView savingsIdeaDateOfCreation,
                savingsIdeaDescription, savingsIdeaDescriptionFromDatabase,
                savingsIdeaBenefits, savingsIdeaBenefitsFromDatabase,
                savingsIdeaProfitability, savingsIdeaProfitabilityFromDatabase,
                savingsIdeaCategory, savingsIdeaCategoryFromDatabase,
                savingsIdeaNatureOfBenefit, savingsIdeaNatureOfBenefitFromDatabase,
                savingsIdeaWorkArea, savingsIdeaWorkAreaFromDatabase,
                savingsIdeaTypeOfCosts, savingsIdeaTypeOfCostsFromDatabase,
                savingsIdeaAverage;

        private ImageView savingsIdea1star, savingsIdea2star,
                savingsIdea3star, savingsIdea4star, savingsIdea5star;

        private ConstraintLayout savingsIdeaFrame, savingsIdeaContraintWithStars;
//        private CustomListener mCustomListener;

//        private SavingsIdeasListAdapter.SavingsIdeaViewHolder.ReadNewsListener readNewsListener;

        public SavingsIdeaViewHolder(@NonNull View itemView) {
            super(itemView);

            savingsIdeaTitle = itemView.findViewById(R.id.savingsIdeaTitle);
            savingsIdeaDateOfCreation = itemView.findViewById(R.id.savingsIdeaCreationData);
            savingsIdeaDescription = itemView.findViewById(R.id.savingsIdeaDescription);
            savingsIdeaDescriptionFromDatabase = itemView.findViewById(R.id.savingsIdeaDescriptionFromDatabase);
            savingsIdeaBenefits = itemView.findViewById(R.id.savingsIdeaBenefits);
            savingsIdeaBenefitsFromDatabase = itemView.findViewById(R.id.savingsIdeaBenefitsFromDatabase);
            savingsIdeaCategory = itemView.findViewById(R.id.savingsIdeaCategory);
            savingsIdeaCategoryFromDatabase = itemView.findViewById(R.id.savingsIdeaCategoryFromDatabase);
            savingsIdeaTypeOfCosts = itemView.findViewById(R.id.savingsIdeaTypeOfCosts);
            savingsIdeaTypeOfCostsFromDatabase = itemView.findViewById(R.id.savingsIdeaTypeOfCostsFromDatabase);
            savingsIdeaProfitability = itemView.findViewById(R.id.savingsIdeaProfitability);
            savingsIdeaProfitabilityFromDatabase = itemView.findViewById(R.id.savingsIdeaProfitabilityFromDatabase);
            savingsIdeaNatureOfBenefit = itemView.findViewById(R.id.savingsIdeaNatureOfBenefit);
            savingsIdeaNatureOfBenefitFromDatabase = itemView.findViewById(R.id.savingsIdeaNatureOfBenefitFromDatabase);
            savingsIdeaWorkArea = itemView.findViewById(R.id.savingsIdeaWorkArea);
            savingsIdeaWorkAreaFromDatabase = itemView.findViewById(R.id.savingsIdeaWorkAreaFromDatabase);

            savingsIdea1star = itemView.findViewById(R.id.savingsIdea1star);
            savingsIdea2star = itemView.findViewById(R.id.savingsIdea2star);
            savingsIdea3star = itemView.findViewById(R.id.savingsIdea3star);
            savingsIdea4star = itemView.findViewById(R.id.savingsIdea4star);
            savingsIdea5star = itemView.findViewById(R.id.savingsIdea5star);
            savingsIdeaContraintWithStars = itemView.findViewById(R.id.savingsIdeaContraintWithStars);


            savingsIdeaAverage = itemView.findViewById(R.id.savingsIdeaAverage);

            savingsIdeaFrame = itemView.findViewById(R.id.savingsIdeaFrame);

            hideSubTextViews();
        }

        void bind(Context context, final SavingsIdea singleSavingsIdea) {

            savingsIdeaTitle.setText(singleSavingsIdea.getIdeaSubject());
            savingsIdeaDateOfCreation.setText(singleSavingsIdea.getDateOfCreation());
            savingsIdeaDescriptionFromDatabase.setText(singleSavingsIdea.getDescription());
            savingsIdeaBenefitsFromDatabase.setText(singleSavingsIdea.getBenefits());
            savingsIdeaProfitabilityFromDatabase.setText(singleSavingsIdea.getProfitability() + " " + singleSavingsIdea.getUnit());
            savingsIdeaNatureOfBenefitFromDatabase.setText(singleSavingsIdea.getBenefits());
            savingsIdeaWorkAreaFromDatabase.setText(singleSavingsIdea.getWorkAreas().getName());

            int averageRating;
            if (singleSavingsIdea.getAverageRating() != null) {
                averageRating = Math.round(singleSavingsIdea.getAverageRating());
                savingsIdeaAverage.setText("" +context.getResources().getText(R.string.ratedSavingsIdea) + String.valueOf(singleSavingsIdea.getAverageRating()));
                setStarsImages(averageRating);
            } else {
                setStarsForUnrated();
                savingsIdeaAverage.setText(R.string.unratedSavingsIdea);
            }


            savingsIdeaFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (savingsIdeaDateOfCreation.getVisibility() == View.GONE) {
                        showSubTextViews();
                    } else {
                        hideSubTextViews();
                    }


                }
            });

            starImagesListeners(singleSavingsIdea);

        }

        public class SavingsIdeaListener implements View.OnClickListener {


            @Override
            public void onClick(View view) {
                if (savingsIdeaDateOfCreation.getVisibility() == View.GONE) {
                    showSubTextViews();
                } else {
                    hideSubTextViews();
                }

            }
        }


        private void hideSubTextViews() {
            savingsIdeaDateOfCreation.setVisibility(View.GONE);
            savingsIdeaDescription.setVisibility(View.GONE);
            savingsIdeaDescriptionFromDatabase.setVisibility(View.GONE);
            savingsIdeaBenefits.setVisibility(View.GONE);
            savingsIdeaBenefitsFromDatabase.setVisibility(View.GONE);
            savingsIdeaCategory.setVisibility(View.GONE);
            savingsIdeaCategoryFromDatabase.setVisibility(View.GONE);
            savingsIdeaTypeOfCosts.setVisibility(View.GONE);
            savingsIdeaTypeOfCostsFromDatabase.setVisibility(View.GONE);
            savingsIdeaProfitability.setVisibility(View.GONE);
            savingsIdeaProfitabilityFromDatabase.setVisibility(View.GONE);
            savingsIdeaNatureOfBenefit.setVisibility(View.GONE);
            savingsIdeaNatureOfBenefitFromDatabase.setVisibility(View.GONE);
            savingsIdeaWorkArea.setVisibility(View.GONE);
            savingsIdeaWorkAreaFromDatabase.setVisibility(View.GONE);
            savingsIdeaContraintWithStars.setVisibility(View.GONE);
            savingsIdeaAverage.setVisibility(View.GONE);

        }

        private void showSubTextViews() {
            savingsIdeaDateOfCreation.setVisibility(View.VISIBLE);
            savingsIdeaDescription.setVisibility(View.VISIBLE);
            savingsIdeaDescriptionFromDatabase.setVisibility(View.VISIBLE);
            savingsIdeaBenefits.setVisibility(View.VISIBLE);
            savingsIdeaBenefitsFromDatabase.setVisibility(View.VISIBLE);
            savingsIdeaCategory.setVisibility(View.VISIBLE);
            savingsIdeaCategoryFromDatabase.setVisibility(View.VISIBLE);
            savingsIdeaTypeOfCosts.setVisibility(View.VISIBLE);
            savingsIdeaTypeOfCostsFromDatabase.setVisibility(View.VISIBLE);
            savingsIdeaProfitability.setVisibility(View.VISIBLE);
            savingsIdeaProfitabilityFromDatabase.setVisibility(View.VISIBLE);
            savingsIdeaNatureOfBenefit.setVisibility(View.VISIBLE);
            savingsIdeaNatureOfBenefitFromDatabase.setVisibility(View.VISIBLE);
            savingsIdeaWorkArea.setVisibility(View.VISIBLE);
            savingsIdeaWorkAreaFromDatabase.setVisibility(View.VISIBLE);
            savingsIdeaContraintWithStars.setVisibility(View.VISIBLE);
            savingsIdeaAverage.setVisibility(View.VISIBLE);

        }


        private void starImagesListeners(SavingsIdea savingsIdea){
            savingsIdea1star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    savingsIdea1star.setImageResource(R.drawable.ic_star_icon_stylized);
                    savingsIdea2star.setImageResource(R.drawable.ic_star_icon_unpressed);
                    savingsIdea3star.setImageResource(R.drawable.ic_star_icon_unpressed);
                    savingsIdea4star.setImageResource(R.drawable.ic_star_icon_unpressed);
                    savingsIdea5star.setImageResource(R.drawable.ic_star_icon_unpressed);
                    sendRating(savingsIdea, 1);
                }
            });
            savingsIdea2star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    savingsIdea1star.setImageResource(R.drawable.ic_star_icon_stylized);
                    savingsIdea2star.setImageResource(R.drawable.ic_star_icon_stylized);
                    savingsIdea3star.setImageResource(R.drawable.ic_star_icon_unpressed);
                    savingsIdea4star.setImageResource(R.drawable.ic_star_icon_unpressed);
                    savingsIdea5star.setImageResource(R.drawable.ic_star_icon_unpressed);
                    sendRating(savingsIdea, 2);
                }
            });
            savingsIdea3star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    savingsIdea1star.setImageResource(R.drawable.ic_star_icon_stylized);
                    savingsIdea2star.setImageResource(R.drawable.ic_star_icon_stylized);
                    savingsIdea3star.setImageResource(R.drawable.ic_star_icon_stylized);
                    savingsIdea4star.setImageResource(R.drawable.ic_star_icon_unpressed);
                    savingsIdea5star.setImageResource(R.drawable.ic_star_icon_unpressed);
                    sendRating(savingsIdea, 3);
                }
            });
            savingsIdea4star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    savingsIdea1star.setImageResource(R.drawable.ic_star_icon_stylized);
                    savingsIdea2star.setImageResource(R.drawable.ic_star_icon_stylized);
                    savingsIdea3star.setImageResource(R.drawable.ic_star_icon_stylized);
                    savingsIdea4star.setImageResource(R.drawable.ic_star_icon_stylized);
                    savingsIdea5star.setImageResource(R.drawable.ic_star_icon_unpressed);
                    sendRating(savingsIdea, 4);
                }
            });
            savingsIdea5star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    savingsIdea1star.setImageResource(R.drawable.ic_star_icon_stylized);
                    savingsIdea2star.setImageResource(R.drawable.ic_star_icon_stylized);
                    savingsIdea3star.setImageResource(R.drawable.ic_star_icon_stylized);
                    savingsIdea4star.setImageResource(R.drawable.ic_star_icon_stylized);
                    savingsIdea5star.setImageResource(R.drawable.ic_star_icon_stylized);
                    sendRating(savingsIdea, 5);
                }
            });

        }


        private void setStarsImages(int average) {
            if (average == 1) {
                savingsIdea1star.setImageResource(R.drawable.ic_star_icon_stylized);
                savingsIdea2star.setImageResource(R.drawable.ic_star_icon_unpressed);
                savingsIdea3star.setImageResource(R.drawable.ic_star_icon_unpressed);
                savingsIdea4star.setImageResource(R.drawable.ic_star_icon_unpressed);
                savingsIdea5star.setImageResource(R.drawable.ic_star_icon_unpressed);
            } else if (average == 2) {
                savingsIdea1star.setImageResource(R.drawable.ic_star_icon_stylized);
                savingsIdea2star.setImageResource(R.drawable.ic_star_icon_stylized);
                savingsIdea3star.setImageResource(R.drawable.ic_star_icon_unpressed);
                savingsIdea4star.setImageResource(R.drawable.ic_star_icon_unpressed);
                savingsIdea5star.setImageResource(R.drawable.ic_star_icon_unpressed);
            } else if (average == 3) {
                savingsIdea1star.setImageResource(R.drawable.ic_star_icon_stylized);
                savingsIdea2star.setImageResource(R.drawable.ic_star_icon_stylized);
                savingsIdea3star.setImageResource(R.drawable.ic_star_icon_stylized);
                savingsIdea4star.setImageResource(R.drawable.ic_star_icon_unpressed);
                savingsIdea5star.setImageResource(R.drawable.ic_star_icon_unpressed);
            } else if (average == 4) {
                savingsIdea1star.setImageResource(R.drawable.ic_star_icon_stylized);
                savingsIdea2star.setImageResource(R.drawable.ic_star_icon_stylized);
                savingsIdea3star.setImageResource(R.drawable.ic_star_icon_stylized);
                savingsIdea4star.setImageResource(R.drawable.ic_star_icon_stylized);
                savingsIdea5star.setImageResource(R.drawable.ic_star_icon_unpressed);
            } else if (average == 5) {
                savingsIdea1star.setImageResource(R.drawable.ic_star_icon_stylized);
                savingsIdea2star.setImageResource(R.drawable.ic_star_icon_stylized);
                savingsIdea3star.setImageResource(R.drawable.ic_star_icon_stylized);
                savingsIdea4star.setImageResource(R.drawable.ic_star_icon_stylized);
                savingsIdea5star.setImageResource(R.drawable.ic_star_icon_stylized);

            }
        }

        private void setStarsForUnrated(){
            savingsIdea1star.setImageResource(R.drawable.ic_star_icon_unpressed);
            savingsIdea2star.setImageResource(R.drawable.ic_star_icon_unpressed);
            savingsIdea3star.setImageResource(R.drawable.ic_star_icon_unpressed);
            savingsIdea4star.setImageResource(R.drawable.ic_star_icon_unpressed);
            savingsIdea5star.setImageResource(R.drawable.ic_star_icon_unpressed);
        }


        private void sendRating(SavingsIdea savingsIdea, int rating){
            String token = "Bearer " + loggedUser.getToken();
            Long senderId = loggedUser.getExternalId();
            System.out.println("id zalogowanego uzytkownika: " + senderId);

            Long savingIdeaId = savingsIdea.getExternalId();


            Call<Void> call = ApiUtils.getApiService().sendSavingsIdeaRating(token, rating, savingIdeaId, senderId);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 200){
                        System.out.println("udało się zapisać dane teraz odśwież strone");


                        Activity activity = ((AppCompatActivity) context);
                        ((MainActivity)activity).savingsIdeasFragment();


                    }else if (response.code() == 500){
                        System.out.println("token wygasl");

                    }



                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
                    System.out.println("nie udało się");
                }

            });

        }



        public class CustomListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
            }
        }
    }
}