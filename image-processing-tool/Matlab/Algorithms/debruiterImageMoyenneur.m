function [img] = debruiterImageMoyenneur(M)
    img = M;
    longueur = size(img,1);
    largeur = size(img,2);
    
    for y=2:longueur-2
        for x=2:largeur-2
            liste = sousMatrixGeneral(img,x,y,'normal');
            %Convertir le matrice d'entier du meme type que les pixel de
            %l'image (uint8)
            liste = double(liste);
            pc = sum(liste(:))/length(liste);
            img(x,y) = pc;
        end
    end
end

