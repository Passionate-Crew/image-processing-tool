function [img] = getBordImage(M)
    img = M;
    longueur = size(img,1);
    largeur = size(img,2);
    T = [];
    i=1;
    
    for y=2:longueur-2
        for x=2:largeur-2
            FiltreVertical = [-1 0 1
                              -2 0 2
                              -1 0 1];
            G = sousMatrixGeneral(img, x, y, 'normal');
            I = double(G);
            res = sum(FiltreVertical(:).*I(:));
            if(res > 10)
                X = [x, y];
                T = vertcat(T,X);
            end

        end
    end
    for y=2:longueur-2
        for x=2:largeur-2
            FiltreVertical = [-1 -2 -1
                               0  0  0
                              -1 -2 -1];
            G = sousMatrixGeneral(img,x,y,'normal');
            I = double(G);
            res = sum(FiltreVertical(:).*I(:));
            if(res > 10)
                X = [x, y];
                T = vertcat(T,X);
            end
        end
    end

    for y=2:longueur-2
        for x=2:largeur-2
            if(T(y,1) == x && T(y,2) == y)
                img(x,y) =255;
            else
                img(x,y)=0;
            end
            i=i+1;
        end
    end
end

