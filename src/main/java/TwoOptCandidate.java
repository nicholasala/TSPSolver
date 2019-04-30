public class TwoOptCandidate extends TSPAlgorithm {
    private int size;

    //TODO il twoopt con candidate non da gli stessi valori che da il two opt normale, e in molti casi genera soluzioni con un errore maggiore
    TwoOptCandidate(MapHandler map, Tour defaultTour) {
        super(map);
        tour = defaultTour;
        size = tour.size();
    }

    @Override
    public Tour startTour() {
        int best_gain = -1, gain, best_i = 0, best_k = 0;
        tour.generatePositionsMap();

        while(best_gain < 0){
            best_gain = 0;

            for(int i=0; i<size; i++){
                for(int k=0; k<map.candidateLinkNum(tour.get(i)); k++){
                    gain = computeGain(i, k);

                    if(gain < best_gain){
                        best_gain = gain;
                        best_i = i;
                        best_k = k;
                    }
                }
            }

            //scambio
            if(best_gain < 0)
                computeSwap((best_i + 1)%tour.size(), tour.getPosition(map.candidates[tour.get(best_i) - 1][best_k]));
        }

        return tour;
    }

    private int computeGain(int i, int k){
        int id1 = tour.get(i);
        int id2 = tour.get((i+1) % tour.size());
        int id3 = map.candidates[tour.get(i) - 1][k];
        int id4 = tour.get((tour.getPosition(id3)+1) % tour.size());

        return (map.distById(id1, id3) + map.distById(id2, id4))
                - (map.distById(id1, id2) + map.distById(id3, id4));
    }

    private void computeSwap(int firstIndex, int secondIndex){
        while(firstIndex > secondIndex){
            tour.swapAndRefreshPos(firstIndex, secondIndex);
            firstIndex++;
            secondIndex--;

            if(firstIndex == size)
                firstIndex = 0;
            if(secondIndex == -1)
                secondIndex = size - 1;
        }
        while(firstIndex < secondIndex){
            tour.swapAndRefreshPos(firstIndex, secondIndex);
            firstIndex++;
            secondIndex--;
        }
    }
}
