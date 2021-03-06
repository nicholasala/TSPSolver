public class TwoOpt extends TSPAlgorithm {

    TwoOpt(MapHandler map, Tour defaultTour) {
        super(map);
        tour = defaultTour;
    }

    @Override
    public Tour startTour() {
        int size = tour.size(), best_gain = -1, gain, best_i = 0, best_k = 0;

        while(best_gain < 0){
            best_gain = 0;

            for(int i=0; i<size-1; i++){
                for(int k=i+2; k<size; k++){
                    gain = computeGain(i, k);

                    if(gain < best_gain){
                        best_gain = gain;
                        best_i = i;
                        best_k = k;
                    }
                }
            }

            //Terza versione,
            if(size >= 318 && best_gain < 0)
                computeSwap(best_i+1, best_k);
            else if(best_gain < 0)
                tour.swap(best_i+1, best_k);

            //scambio come andrebbe fatto
            //if(best_gain < 0)
                //computeSwap(best_i+1, best_k);
        }

        return tour;
    }

    //Bisogna gestire il caso in cui k sia uguale a size-1, in quel caso il k+1 elemento sarà la prima città del tour
    private int computeGain(int i, int k){
        return (map.distById(tour.get(i), tour.get(k)) + map.distById(tour.get(i+1), tour.get((k+1) % tour.size())))            //tour modificato
                - (map.distById(tour.get(i), tour.get(i+1)) + map.distById(tour.get(k), tour.get((k+1) % tour.size())));        //tour attuale
    }

    private void computeSwap(int firstIndex, int secondIndex){
        while(firstIndex < secondIndex){
            tour.swap(firstIndex, secondIndex);
            firstIndex++;
            secondIndex--;
        }
    }
}
