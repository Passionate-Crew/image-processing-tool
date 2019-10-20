function [img] = debruiterPoivreEtSelGeneral(M)
    img = M;
    tailleX = size(img,1);
    tailleY = size(img,2);
    %Creer une matrice pleine de zero pour l'image prochaine
    %R = zeros(tailleX, tailleY);
    for y=2:tailleY-2
        for x=2:tailleX-2
            if(img(x,y) == 0 || img(x,y) == 255)
                L = sousMatrixGeneral(img, x, y, 'normal');
                S = triBulle(L);
                p = median(single(S));
                img(x,y)= p;
            end
        end
    end
end

