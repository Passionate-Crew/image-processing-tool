function [img] = BruiterImageGaussian(M)
    img = M;
    longueur = size(img, 1);
    largeur = size(img, 1);
    
    for y = 1:longueur
        for x = 1:largeur
            g = img(x, y);
            a = 20;
            r = a.*randn(1);
            %r = (1/(sqrt(2*pi)*0.6))*(-exp((g-1).^2/(2*0.6.^2)));
            img(x, y) = g + r;
        end
    end
end