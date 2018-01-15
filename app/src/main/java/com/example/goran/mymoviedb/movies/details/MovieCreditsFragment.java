package com.example.goran.mymoviedb.movies.details;


import android.support.v4.app.Fragment;

import com.example.goran.mymoviedb.data.model.singlemovie.MovieCredits;

/**
 * Created by Goran on 25.12.2017..
 */

public class MovieCreditsFragment extends Fragment implements MovieCreditsContract.View {
    @Override
    public void displayCredits(MovieCredits movieCredits) {

    }

    /*private static final String IMG_BASE_URL = "https://image.tmdb.org/t/p/w300";

    ApiHelper apiHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Fresco.initialize(getActivity());
        return inflater.inflate(R.layout.fragment_movie_credits, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Intent intent = getActivity().getIntent();
        int movieId = intent.getIntExtra("movie_id", 0);

        TextView txtDirector = getView().findViewById(R.id.txt_credits_director);
        SimpleDraweeView imgDirector = getView().findViewById(R.id.img_credits_director);

        TextView txtWriter = getView().findViewById(R.id.txt_credits_writer);
        SimpleDraweeView imgWriter = getView().findViewById(R.id.img_credits_writer);

        RecyclerView recyclerView = getView().findViewById(R.id.recycler_credits_cast);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Cast> cast = new ArrayList<>();

        CastAdapter castAdapter = new CastAdapter(cast);

        recyclerView.setAdapter(castAdapter);

        Observable<MovieCredits> observable = apiHelper.getMovieCredits(movieId);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieCredits -> {

                    String director = "";
                    String directorProfileUrl = "";

                    for (Crew crew : movieCredits.getCrew()) {
                        Log.i("JOB", crew.getJob());
                        if (crew.getJob().matches("Director")) {
                            director = crew.getName();
                            directorProfileUrl = crew.getProfilePath();
                        }
                    }

                    txtDirector.setText(director);
                    imgDirector.setImageURI(Uri.parse(IMG_BASE_URL + directorProfileUrl));

                    String writer = "";
                    String writerProfileUrl = "";

                    for (Crew crew : movieCredits.getCrew()) {
                        if (crew.getJob().matches("Screenplay")) {
                            writer = crew.getName();
                            writerProfileUrl = crew.getProfilePath();
                        }
                    }

                    txtWriter.setText(writer);
                    imgWriter.setImageURI(Uri.parse(IMG_BASE_URL + writerProfileUrl));

                    cast.addAll(movieCredits.getCast());
                    castAdapter.notifyDataSetChanged();
                });

    }*/
}
