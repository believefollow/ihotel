import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Ck2xsyDetailComponent } from './ck-2-xsy-detail.component';

describe('Component Tests', () => {
  describe('Ck2xsy Management Detail Component', () => {
    let comp: Ck2xsyDetailComponent;
    let fixture: ComponentFixture<Ck2xsyDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [Ck2xsyDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ ck2xsy: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(Ck2xsyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Ck2xsyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ck2xsy on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ck2xsy).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
