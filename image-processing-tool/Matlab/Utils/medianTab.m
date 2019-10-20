function [res] = medianTab(L)
    taille = length(L);
    if(mod(taille/2,2) == 0)
        res = L(taille/2);
    else
        res = (L(floor(taille/2)-1)+L(floor(taille/2)+1))/2;
    end

end

