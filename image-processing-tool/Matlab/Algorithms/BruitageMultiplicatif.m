image1 = afficherImage(chemin);
image2 = BruiterImageMultiplicatif(image1);
i=0;
while(i<1)
    image2 = BruiterImageMultiplicatif(image2);
    i=i+1;
end
snr = SNR(image1, image2)
f=figure(1);
%Modifie l'image en [x, y, largeur, longueur]
set(f,'Position',[150 150 1200 600])
subplot(1,2,1)
imagesc(image1)
subplot(1,2,2)
imagesc(image2)
colormap(gray)