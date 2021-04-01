import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FtXzsDetailComponent } from './ft-xzs-detail.component';

describe('Component Tests', () => {
  describe('FtXzs Management Detail Component', () => {
    let comp: FtXzsDetailComponent;
    let fixture: ComponentFixture<FtXzsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [FtXzsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ ftXzs: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(FtXzsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FtXzsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ftXzs on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ftXzs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
