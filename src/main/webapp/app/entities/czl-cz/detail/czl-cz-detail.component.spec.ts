import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CzlCzDetailComponent } from './czl-cz-detail.component';

describe('Component Tests', () => {
  describe('CzlCz Management Detail Component', () => {
    let comp: CzlCzDetailComponent;
    let fixture: ComponentFixture<CzlCzDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CzlCzDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ czlCz: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CzlCzDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CzlCzDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load czlCz on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.czlCz).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
