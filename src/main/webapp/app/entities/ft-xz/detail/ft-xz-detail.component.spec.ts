import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FtXzDetailComponent } from './ft-xz-detail.component';

describe('Component Tests', () => {
  describe('FtXz Management Detail Component', () => {
    let comp: FtXzDetailComponent;
    let fixture: ComponentFixture<FtXzDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [FtXzDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ ftXz: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(FtXzDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FtXzDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ftXz on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ftXz).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
