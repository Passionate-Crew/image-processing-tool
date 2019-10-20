function [img] = BruiterImageMultiplicatif(M)
    img = M;
    longueur = size(img, 1);
    largeur = size(img, 1);
    
    for y = 1:longueur
        for x = 1:largeur
            g = img(x, y);
            a = 0.1;
            r = a.*randn(1);
            %r = (1/(sqrt(2*pi)*0.6))*(-exp((g-1).^2/(2*0.6.^2)));
            img(x, y) = g*(1+r);
        end
    end
end

