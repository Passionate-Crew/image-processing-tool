function [img] = BruiterImagePoivreEtSel(M)
    img = M;
    tailleX = size(img,1);
    tailleY = size(img,2);
    
    for y=1:tailleY
        for x=1:tailleX
            r = randi([0,255],1);
            if(r == 0)
                img(x,y) = 0;
            else
                if(r == 255)
                    img(x,y) = 255;
                end
            end
        end
    end
    
end

