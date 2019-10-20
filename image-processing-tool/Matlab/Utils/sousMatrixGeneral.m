function [S] = sousMatrixGeneral(M, x, y, type)
    if(strcmp(type, 'normal'))
        S = M(x-1:x+1,y-1:y+1);
    else
        if(strcmp(type, 'angleHG'))
             S = M();
        else
            if(strcmp(type, 'angleHG'))
                S = M();
            else
                if(strcmp(type, 'bordD'))
                    S = M();
                else
                    if(strcmp(type, 'bordG'))
                        S = M();
                    else
                        if(strcmp(type, 'bordH'))
                            S = M();
                        else
                            if(strcmp(type, 'bordB'))
                                S = M();
                            end
                        end
                    end
                end
            end
        end
    end
end

