image3 = debruiterPoivreEtSelGeneral(image2);
snr = SNR(image1, image3)
f=figure(2);
%Modifie l'image en [x, y, largeur, longueur]
set(f,'Position',[150 150 1200 600])
subplot(1,2,1)
imagesc(image1)
subplot(1,2,2)
imagesc(image3)
colormap(gray)