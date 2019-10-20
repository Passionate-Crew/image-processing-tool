function [img] = debruiterGaussian(M)
    img = M;
    longueur = size(img,1);
    largeur = size(img,2);
    
    for y=2:longueur-2
        for x=2:largeur-2
            liste = sousMatrixGeneral(img,x,y,'normal');
            poids = [1 2 1 ; 2 4 2 ; 1 2 1];
            %Convertir le matrice d'entier du meme type que les pixel de
            %l'image (uint8)
            liste = double(liste);
            S = liste.*poids;
            pc = sum(S(:))/sum(poids(:));
            img(x,y) = pc;
        end
    end
end

