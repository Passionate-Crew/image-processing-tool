function [im1] = afficherImage(chemin)
    im = imread(chemin);
    im1 = im(:,:,1);
end

